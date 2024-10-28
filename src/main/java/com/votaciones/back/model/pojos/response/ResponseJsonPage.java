package com.votaciones.back.model.pojos.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ResponseJsonPage {

        private List<Object> content;
        private int page;
        private int size;
        private Long totalElements;
        private int totalPages;
}
