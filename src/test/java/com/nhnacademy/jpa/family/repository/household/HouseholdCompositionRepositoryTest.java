package com.nhnacademy.jpa.family.repository.household;

import com.nhnacademy.jpa.family.config.RootConfig;
import com.nhnacademy.jpa.family.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class HouseholdCompositionRepositoryTest {
    @Autowired
    private HouseholdCompositionRepository compositionRepository;

    @Test
    void getHouseholderSnByMemberSn() {
        int memberSn = 5;
        Integer householderSnByMemberSn = compositionRepository.getHouseholderSnByMemberSn(memberSn);
        assertThat(householderSnByMemberSn).isEqualTo(4);
    }
}
