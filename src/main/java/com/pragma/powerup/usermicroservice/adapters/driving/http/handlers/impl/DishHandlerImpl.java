package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UpdateDishRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IUpdateDishRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IUpdateDishRequestMapper updateDishRequestMapper;

    @Override
    public void createDish(DishRequestDto dishRequestDto) {
        dishServicePort.createDish(dishRequestMapper.requestToDish(dishRequestDto));
    }

    @Override
    public void updateDish(UpdateDishRequestDto updateDishRequestDto) {
        dishServicePort.updateDish(updateDishRequestMapper.requestToDish(updateDishRequestDto));
    }
}
