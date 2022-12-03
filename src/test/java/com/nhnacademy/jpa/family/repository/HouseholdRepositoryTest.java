package com.nhnacademy.jpa.family.repository;

import com.nhnacademy.jpa.family.config.RootConfig;
import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.domain.HouseholderDto;
import com.nhnacademy.jpa.family.domain.SerialNumberOnly;
import com.nhnacademy.jpa.family.entity.Household;
import com.nhnacademy.jpa.family.entity.HouseholdMovementAddress;
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
    void findFirstByOrderBySerialNumberDesc() {
        SerialNumberOnly first = householdRepository.findFirstByOrderBySerialNumberDesc();
        System.out.println("first = " + first.getSerialNumber());
    }

    @Test
    void getDtoBySerialNumber() {
        int sn = 4;
        List<HouseholderDto> dtos = householdRepository.getDtoBySerialNumber(sn);
        assertThat(dtos.size()).isEqualTo(3);

        List<HouseholderDto> dtoHasLastAddress = dtos.stream()
                .filter(it -> it.getYesOrNo().equals(HouseholdMovementAddress.LAST_YES))
                .peek(System.out::println)
                .filter(it -> it.getMovementAddress().contains("경기도"))
                .collect(Collectors.toList());

        assertThat(dtoHasLastAddress.size() > 0).isTrue();

    }
}
