package com.example.Spring_Security_Latest.controller;

import com.example.Spring_Security_Latest.dto.AuthRequest;
import com.example.Spring_Security_Latest.dto.Product;
import com.example.Spring_Security_Latest.entity.UserInfo;
import com.example.Spring_Security_Latest.service.JwtService;
import com.example.Spring_Security_Latest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @GetMapping("/welcome")
    public String welcome(){
      return "Welcome. This endpoint is not secured";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //this annotation is used to give authority to the admin only, so he/she can fetch all
    //the details only, but not the user
    public List<Product> allProducts(){
        return service.getProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    //only user will be able to fetch the details using the product id.
    public Product productWithID(@PathVariable int id){
        return service.getProductById(id);
    }

    @PostMapping("/new")
    public String addUser(@RequestBody UserInfo info){
        return service.addUser(info);
    }

    @PostMapping("/authenticate")
    public String authenticateGetToken(@RequestBody AuthRequest authRequest){
        //without authtication of the user, it will generate for whom so ever enters it details, whether
        //its data present in db or not. which is not what someone would want.
        //In spring security flow, filter ---> authenticationManager


        Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }

    }
}
