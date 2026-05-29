package org.fmm.librosparroquiales.common.repository;

import org.fmm.librosparroquiales.common.model.BaptismalCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by felix.merino.
 * @author Félix merino
 */
@Repository
public interface BaptismalCandidateRepository extends JpaRepository<BaptismalCandidate, Integer> {
}
