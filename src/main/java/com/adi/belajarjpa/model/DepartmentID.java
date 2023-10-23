package com.adi.belajarjpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentID implements Serializable {

    /*
    * class ini akan mengembed id ke class Department.
    * khusus untuk class embedded untuk primary key, di rekomendasikan implement serilizable.
    * */

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "department_id")
    private Integer departmentId;

}
