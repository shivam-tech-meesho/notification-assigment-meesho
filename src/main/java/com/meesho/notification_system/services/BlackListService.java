package com.meesho.notification_system.services;

import java.util.List;
import java.util.Set;

public interface BlackListService {

    public void addToBlockList(List<String> numbers);

    public void removeFromBlockList(List<String> numbers);

    public boolean isBlocked(String number);

}
