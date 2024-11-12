package com.Openfeign.ms_registros1.client;

import com.Openfeign.ms_registros1.aggregates.response.ResponseSunat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="client-sunat",url = "https://api.apis.net.pe/v2/sunat/")
public interface ClientSunat {
@GetMapping("/ruc/full")
ResponseSunat getPersonaSunat(@RequestParam("numero") String numero,
                              @RequestHeader("authorization") String authorization);

}
