package inssait.model.dao;

import org.springframework.data.repository.CrudRepository;

import inssait.model.domain.Influencers;

public interface InfluencersRepository extends CrudRepository<Influencers, String> {

}
