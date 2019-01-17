package org.psc.gen.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

@Entity
@Data
@Wither
@AllArgsConstructor
public class ContractEntity implements GenericData {
    
    @Id
    private Long contractId;
    
    @Lob
    private byte[] genericData;

}
