package com.busanit.petgathering.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.busanit.petgathering.dto.DeliveryDto;
import com.busanit.petgathering.dto.MemberFormDto;
import com.busanit.petgathering.dto.OrderDto;
import com.busanit.petgathering.dto.OrderHistDto;
import com.busanit.petgathering.entity.Delivery;
import com.busanit.petgathering.service.DeliveryService;
import com.busanit.petgathering.service.MemberService;
import com.busanit.petgathering.service.OrderService;
import com.busanit.petgathering.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final MemberService memberService;

    private final DeliveryService deliveryService;

    @GetMapping(value = {"/post", "/post/{page}"})
    public String orderPost(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(CommonUtil.getId(principal.getName()), pageable);
        MemberFormDto memberFormDto = memberService.getMypageList(CommonUtil.getId(principal.getName()));

        model.addAttribute("deliveryDto",new DeliveryDto());
        model.addAttribute("memberFormDto", memberFormDto);
        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage",5);
        return "order/orderItem";
    }
    
    @GetMapping(value = {"/cartPost", "/cartPost/{page}"})
    public String cartOrderPost(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(CommonUtil.getId(principal.getName()), pageable);
        MemberFormDto memberFormDto = memberService.getMypageList(CommonUtil.getId(principal.getName()));

        model.addAttribute("deliveryDto",new DeliveryDto());
        model.addAttribute("memberFormDto", memberFormDto);
        model.addAttribute("cartOrders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage",5);
        return "order/cartPay";
    }


    @PostMapping(value = "/post")
    public String newDelivery(@Valid DeliveryDto deliveryDto, @RequestParam("orderId") Long orderId, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "order/orderItem";
        }
        try {
            orderService.changeOrder(orderId);
            deliveryService.saveDelivery(deliveryDto, orderId);
        } catch (Exception e){
            model.addAttribute("errorMessage","배송지 등록 중 에러가 발생하였습니다.");
            return "order/orderItem";
        }

        return "redirect:/orders";
    }
    
    @PostMapping(value = "/cartPost")
    public String newCartDelivery(@Valid DeliveryDto deliveryDto, @RequestParam("orderId") Long orderId, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "order/cartPay";
        }
        try {
            orderService.changeOrder(orderId);
            deliveryService.saveDelivery(deliveryDto, orderId);
        } catch (Exception e){
            model.addAttribute("errorMessage","배송지 등록 중 에러가 발생하였습니다.");
            return "order/cartPay";
        }

        return "redirect:/orders";
    }
    
    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult,
                                              Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String id = CommonUtil.getId(principal.getName());
        Long orderId;

        try{
            orderId = orderService.order(orderDto,id);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
    
    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(CommonUtil.getId(principal.getName()), pageable);
        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage",5);
        return "order/orderHist";
    }


    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId")Long orderId, Principal principal){

        if(!orderService.validateOrder(orderId, CommonUtil.getId(principal.getName()))){
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/order/delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") Long orderId){
        orderService.deleteOrder(orderId);
        return "redirect:/admin/orders";
    }

    @GetMapping(value = {"/admin/orders", "/admin/orders/{page}"})
    public String deliveryList(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<OrderHistDto> orderHistDtoList = orderService.getOrderList(pageable);
        List<Delivery> deliveryList = deliveryService.findDelivery();
        model.addAttribute("deliveryList", deliveryList);
        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage",5);

        return "/order/orderMng";
    }


    @GetMapping(value = "/order/delete/{orderId}")
    public String deleteOrders(@PathVariable("orderId") Long orderId){
        orderService.deleteOrder(orderId);
        return "redirect:/post";
    }
}

