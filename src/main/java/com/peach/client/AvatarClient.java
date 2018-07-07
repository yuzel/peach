package com.peach.client;

import com.peach.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "avatar", url = "http://avatar.service.alphafish")
public interface AvatarClient {

  /**
   * 静态信息
   *
   * @return AvatarStaticInfoResp
   */
  @GetMapping("/internal_api/v4/static/infos")
  Result getStaticInfo();

}
