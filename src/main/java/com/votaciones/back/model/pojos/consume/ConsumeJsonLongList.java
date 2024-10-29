package com.votaciones.back.model.pojos.consume;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ConsumeJsonLongList {
    List<Long> ids;
}
