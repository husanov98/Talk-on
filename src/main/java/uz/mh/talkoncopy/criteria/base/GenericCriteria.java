package uz.mh.talkoncopy.criteria.base;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

public class GenericCriteria implements BaseGenericCriteria, Serializable {


    protected Integer page;

    protected Integer size;

    public Integer getPage() {
        if (this.page == null || this.page < 1) {
            page = 1;
        }
        return page;
    }
    public Integer getSize(){
        if (Objects.isNull(this.size) || this.size < 1){
            size = 10;
        }
        return size;
    }

//    protected String sortBy;
//
//    protected String sortDescription;
//
//    public String getSortDescription() {
//        return sortDescription == null || sortDescription.equals("") ? " ASC " : sortDescription;
//    }
}
