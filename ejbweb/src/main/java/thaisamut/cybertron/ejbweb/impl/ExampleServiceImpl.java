package thaisamut.cybertron.ejbweb.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import thaisamut.cybertron.ejbweb.remote.ExampleService;

@Component("exampleServiceImpl")
@Transactional
public class ExampleServiceImpl implements ExampleService {

}
