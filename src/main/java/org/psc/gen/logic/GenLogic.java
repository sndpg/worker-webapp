package org.psc.gen.logic;

import org.psc.gen.domain.GenericData;
import org.psc.gen.repo.ContractRepository;
import org.psc.gen.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GenLogic {

    @Autowired
    private ContractRepository contractRepo;

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private Map<String, JpaRepository<GenericData, Long>> repos;

    public <T extends GenericData> void saveEntityWithGenericData(T genericDataEntity) {
        @SuppressWarnings("unchecked")
        JpaRepository<T, Long> repo = (JpaRepository<T, Long>) userRepo;
        
        repo.save(genericDataEntity);
    }
    
}
