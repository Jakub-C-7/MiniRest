package org.example.repository;

import org.example.entities.CustomerDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for CustomerDetails entity.
 */
@Repository
public interface CustomerDetailsRepository extends CrudRepository<CustomerDetails, String> {

}
