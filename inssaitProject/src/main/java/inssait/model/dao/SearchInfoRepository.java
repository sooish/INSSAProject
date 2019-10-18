package inssait.model.dao;

import org.springframework.data.repository.CrudRepository;

import inssait.model.domain.SearchInfo;

public interface SearchInfoRepository extends CrudRepository<SearchInfo, String> {

}
