package com.nhnacademy.jpa.family.repository;

import com.nhnacademy.jpa.family.config.RootConfig;
import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.domain.household.HouseholdCompositionDto;
import com.nhnacademy.jpa.family.domain.household.HouseholderDto;
import com.nhnacademy.jpa.family.domain.SerialNumberOnly;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.entity.HouseholdMovementAddress;
import com.nhnacademy.jpa.family.entity.code.Relationship;
import com.nhnacademy.jpa.family.exception.HouseholdNotFoundException;
import com.nhnacademy.jpa.family.repository.household.HouseholdRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class HouseholdRepositoryTest {
    @Autowired
    private HouseholdRepository householdRepository;

    @Test
    void findById() {
        int id = 1;
        Optional<Household> byId = householdRepository.findById(id);
        Household household = byId.orElseThrow(() -> new HouseholdNotFoundException());

        assertThat(household.getSerialNumber()).isEqualTo(id);
        assertThat(household.getCurrentMovementAddress()).isNotBlank();

        System.out.println("household = " + household);
    }

    @Test
    void findHouseholdByHouseholder_SerialNumber() {
        int residentSn = 4;
        Optional<Household> optionalHousehold = householdRepository.findHouseholdByHouseholder_SerialNumber(residentSn);
        Household household = optionalHousehold.orElseThrow(() -> new HouseholdNotFoundException());
        assertThat(household.getHouseholder().getSerialNumber()).isEqualTo(residentSn);
    }

    @Test
    void findFirstByOrderBySerialNumberDesc() {
        SerialNumberOnly first = householdRepository.findFirstByOrderBySerialNumberDesc();
        System.out.println("first = " + first.getSerialNumber());
    }

    @Test
    void getDtoBySerialNumber() {
        int sn = 4;
        List<HouseholderDto> dtos = householdRepository.getHouseholderDtoBySerialNumber(sn);
        System.out.println("dtos = " + dtos);
        assertThat(dtos.size()).isEqualTo(3);

        List<HouseholderDto> dtoHasLastAddress = dtos.stream()
                .filter(it -> it.getYesOrNo().equals(HouseholdMovementAddress.LAST_YES))
                .peek(System.out::println)
                .filter(it -> it.getMovementAddress().contains("경기도"))
                .collect(Collectors.toList());

        assertThat(dtoHasLastAddress.size() > 0).isTrue();

    }

    @Test
    void getCompositionDtoByHouseholdSerialNumber() {
        int householdSn = 1;
        List<HouseholdCompositionDto> dtos = householdRepository.getCompositionDtoByHouseholdSerialNumber(householdSn);
        System.out.println("dtos = " + dtos);
        assertThat(dtos.size()).isEqualTo(4);

        boolean is남기준 = dtos.stream()
                .filter(it -> it.getRelationship().equals(Relationship.본인))
                .anyMatch(it -> it.getName().equals("남기준"));
        assertThat(is남기준).isTrue();

    }


}
