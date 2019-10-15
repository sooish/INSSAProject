package inssait.model.dao;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import inssait.model.domain.Post;
@Repository
public interface ESPostRepository extends ElasticsearchCrudRepository<Post, Long> {
	@Query("{ \"_source\" : [ \"place\"] , \"query\" : { \"match_all\" : {} }}")
	List<Post> findAll();
}
