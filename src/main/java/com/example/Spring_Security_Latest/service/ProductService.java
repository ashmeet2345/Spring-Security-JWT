package com.example.Spring_Security_Latest.service;

import com.example.Spring_Security_Latest.dto.Product;
import com.example.Spring_Security_Latest.entity.UserInfo;
import com.example.Spring_Security_Latest.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class ProductService {

    List<Product> list=null;

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    //it is used on method that is needed to be ran when dependency injection is done.
    public void loadProductsFromDb(){
        list=new ArrayList<>();
        for(int i=1;i<=100;i++){
            list.add(new Product(i,"product "+i,new Random().nextInt(10),new Random().nextInt(5000)));
        }
    }

    public List<Product> getProducts(){
        return list;
    }

    public Product getProductById(int id){
       return list.stream()
                .filter(product->product.getProductId()==id)
                .findAny()
                .orElseThrow(()->new RuntimeException("Product with id "+id+" not found"));
    }


    public String addUser(UserInfo info){
        info.setPassword(encoder.encode(info.getPassword()));
        repository.save(info);
        return "User added to system";
    }
}
