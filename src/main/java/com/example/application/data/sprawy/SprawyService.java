package com.example.application.data.sprawy;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SprawyService {
    private static SprawyRepository sprawyRepository = null;

    public SprawyService(SprawyRepository sprawyRepository) {
        this.sprawyRepository = sprawyRepository;
    }
    //crud
    public static Optional<Sprawy> get(Long id) {
        return sprawyRepository.findById(id);
    }
    public static List<Sprawy> getSprawy(){
        return sprawyRepository.findAll();
    }

    public Sprawy update(Sprawy entity) {
        return sprawyRepository.save(entity);
    }

    public void delete(Long id) {
        sprawyRepository.deleteById(id);
    }

//    public Page<Sprawy> list(Pageable pageable) {
//        return sprawyRepository.findAll(pageable);
//    }
//
//    public Page<Sprawy> list(Pageable pageable, Specification<Sprawy> filter) {
//        return sprawyRepository.findAll(filter, pageable);
//    }
    public int count() {
        return (int) sprawyRepository.count();
    }

}
