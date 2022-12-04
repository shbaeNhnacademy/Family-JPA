package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.config.RootConfig;
import com.nhnacademy.jpa.family.config.WebConfig;
import com.nhnacademy.jpa.family.entity.HouseholdMovementAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class HouseholdMovementAddressRepositoryTest {
    @Autowired
    private HouseholdMovementAddressRepository addressRepository;

    @Test
    void find() {
        HouseholdMovementAddress.Pk pk = new HouseholdMovementAddress.Pk(2, LocalDate.now());
        Optional<HouseholdMovementAddress> byId = addressRepository.findById(pk);
        HouseholdMovementAddress householdMovementAddress = byId.orElse(new HouseholdMovementAddress());
    }
}
