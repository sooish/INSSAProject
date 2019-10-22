package inssait.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import inssait.model.domain.Influencers;

public interface InfluencersRepository extends CrudRepository<Influencers, String> {
   List<Influencers> findTop10ByOrderByFollowingsDesc();
}