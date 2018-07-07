package com.peach.controller;

import com.peach.client.AvatarClient;
import com.peach.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘宇泽
 * @date 2018/7/5
 */
@RestController
@RequestMapping("admin/")
public class AdminController {

  @Autowired
  private AvatarClient avatarClient;

  @GetMapping("hello")
  public Result helloWorld(){
    return avatarClient.getStaticInfo();
  }

}
