package com.example.demo.service;

import com.example.demo.model.Record;
import com.example.demo.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RecordRepository recordRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Record record = recordRepository.findByUsername(username);
        if (record == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder().username(record.getUsername()).password(record.getPassword()).roles("USER").build();
    }

}
