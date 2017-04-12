package java8.ex03;

import java.util.List;

import java8.data.Person;
import java8.ex03.Method_03_Test.DaoA;

public interface IDao {
	List<Person> findAll();
    
    default IDao getDefaultInstance(){
    	return new DaoImpl();
    }
}
