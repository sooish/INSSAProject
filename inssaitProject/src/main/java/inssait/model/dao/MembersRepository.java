package inssait.model.dao;

import org.springframework.data.repository.CrudRepository;

import inssait.model.domain.Members;

public interface MembersRepository extends CrudRepository<Members, String> {

}
