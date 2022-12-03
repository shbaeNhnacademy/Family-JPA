package com.nhnacademy.jpa.family.domain.relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhnacademy.jpa.family.entity.code.BirthPlace;
import com.nhnacademy.jpa.family.entity.code.DeathPlace;
import com.nhnacademy.jpa.family.entity.code.Gender;
import com.nhnacademy.jpa.family.exception.ResidentModifyFailException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentModifyRequest {
    @Nullable
    private String name;
    @Nullable
    private String registrationNumber;
    @Nullable
    private Gender gender;
    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime birthDate;
    @Nullable
    private BirthPlace birthPlace;
    @Nullable
    private String registrationAddress;
    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime deathDate;
    @Nullable
    private DeathPlace deathPlace;
    @Nullable
    private String deathPlaceAddress;

    @JsonIgnore
    public Map<String, Object> getFieldAndValueMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                Object value = declaredField.get(this);

                if (Objects.nonNull(value)) {
                    map.put(declaredField.getName(), value);
                }
            } catch (IllegalAccessException e) {
                throw new ResidentModifyFailException(e);
            }
        }

        return map;
    }
}
