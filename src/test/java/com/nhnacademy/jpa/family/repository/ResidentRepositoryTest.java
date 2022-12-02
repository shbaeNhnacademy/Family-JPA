package com.nhnacademy.jpa.family.repository;

import com.nhnacademy.jpa.family.config.RootConfig;
import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.domain.FamilyRelationDto;
import com.nhnacademy.jpa.family.domain.SerialNumberOnly;
import com.nhnacademy.jpa.family.entity.Resident;
import com.nhnacademy.jpa.family.entity.code.BirthPlace;
import com.nhnacademy.jpa.family.entity.code.Gender;
import com.nhnacademy.jpa.family.exception.ResidentNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class ResidentRepositoryTest {
    @Autowired
    private ResidentRepository residentRepository;

    @Test
    void findById() {
        int sn = 1;
        Optional<Resident> byId = residentRepository.findById(sn);
        Resident resident = byId.orElseThrow(() -> new ResidentNotFoundException());
        assertThat(resident.getName()).isEqualTo("남길동");
        System.out.println("resident = " + resident);
    }

    @Test
    void findByName() {
        String name = "남기준";
        Optional<Resident> byId = residentRepository.findByName(name);
        Resident resident = byId.orElseThrow(() -> new ResidentNotFoundException());
        assertThat(resident.getName()).isEqualTo(name);
        System.out.println("resident = " + resident);
    }

    @Test
    void findByAll() {
        List<Resident> all = residentRepository.findAll();
        assertThat(all.size()).isEqualTo(7);
        assertThat(all.get(1).getName()).isEqualTo("남석환");
        System.out.println("all = " + all);
    }

    @Test
    void getResidentBySnFromFamilyRelationship() {
        int sn = 4;
        List<FamilyRelationDto> residents = residentRepository.getResidentBySnFromFamilyRelationship(sn);
        assertThat(residents.size()).isEqualTo(4);

        for (FamilyRelationDto resident : residents) {
            System.out.println("resident = " + resident.getRelationship());
        }
    }

    @Test
    void save_insert() {
        String registrationNumber = "20221202-3111111";
        Integer latestSerialNumber = residentRepository.findFirstByOrderBySerialNumberDesc().getSerialNumber();
        Resident resident = new Resident(latestSerialNumber,
                "테스트",
                registrationNumber,
                Gender.남,
                LocalDateTime.now(),
                BirthPlace.병원, "" +
                "경남 창원시 마산회원구 중리상곡로 114");
        Resident save = residentRepository.save(resident);
        System.out.println("save = " + save);
        assertThat(save.getGender()).isEqualTo(Gender.남);
        assertThat(save.getRegistrationNumber()).isEqualTo(registrationNumber);
    }

    @Test
    void findFirstByOrderBySerialNumberDesc() {
        SerialNumberOnly numberDesc = residentRepository.findFirstByOrderBySerialNumberDesc();
        System.out.println("numberDesc = " + numberDesc.getSerialNumber());
    }


}
