package com.example.demo.items;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ItemsService {

    private final ItemsRepository itemsRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository, UserRepository userRepository) {
        this.itemsRepository = itemsRepository;
        this.userRepository = userRepository;
    }

    public Items getById(Long id){
       return itemsRepository.getOne(id);
    }

    public Optional<Items> getItems(String name){

        return itemsRepository.findItemsByName(name);
    }

    public boolean exist(String name){
        if (itemsRepository.findItemsByName(name).isPresent()){
            return true;
        }else {
            return false;
        }
    }

    public Items getBest(){

        List items = itemsRepository.findAll(Sort.by(Sort.Direction.DESC,"rate"));
        Items item = (Items) items.get(0);

        return item;
    }

    public void add(Items item){
        if (itemsRepository.findItemsByName(item.getName()).isPresent() == false){
            itemsRepository.save(item);
        }
        else {
            System.out.println("istnieje");
        }

    }

    public void rateUp(Long id, String clientIp){
        Items item = itemsRepository.getOne(id);
        int rate = item.getRate();
        int rateCounter = item.getRateCounter();
        rate += 1;
        rateCounter +=1;
        item.setRate(rate);
        item.setRateCounter(rateCounter);


        if (userRepository.findByUserIp(clientIp).isPresent() == false){
            User user = new User(clientIp);
            userRepository.save(user);
            item.enrollUser(user);
            itemsRepository.save(item);
            System.out.println("nie istnieje user wiec go tworzymy i zapisujemy dodaną ocenę");

        }else if (isRateAdded(id, clientIp) == false){
            item.enrollUser(userRepository.findByUserIp(clientIp).get());
            itemsRepository.save(item);
            System.out.println("user istnieje ale nie dodal oceny do tego przedmiotu wiec dodajemy");
        }

    }

    public void rateDown(Long id, String clientIp){
        Items item = itemsRepository.getOne(id);
        int rate = item.getRate();
        int rateCounter = item.getRateCounter();
        rate -= 1;
        rateCounter +=1;
        item.setRate(rate);
        item.setRateCounter(rateCounter);


        if (userRepository.findByUserIp(clientIp).isPresent() == false){
            User user = new User(clientIp);
            userRepository.save(user);
            item.enrollUser(user);
            itemsRepository.save(item);

        }else if (isRateAdded(id, clientIp) == false){
            item.enrollUser(userRepository.findByUserIp(clientIp).get());
            itemsRepository.save(item);
        }

    }

    private boolean isRateAdded(Long id, String clientIp){

        Iterator<User> iterator = itemsRepository.getOne(id).getEnrolledUser().stream().iterator();

        while (iterator.hasNext()){
            if (iterator.next().getUserIp().equals(clientIp)){
                return true;
            }
        }
        return false;
    }
}












