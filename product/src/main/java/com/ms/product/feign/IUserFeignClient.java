package com.ms.product.feign;

import com.ms.product.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "users", url = "http://localhost:8888")
public interface IUserFeignClient {
    @GetMapping(value = "/users/{user_id}", produces = "application/json", headers = "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNBY3RpdmF0ZWQiOnRydWUsImV4cCI6MTY5OTk5MTQxNiwiaWF0IjoxNjk5OTkxMzg2LCJnZW5lcmF0ZWRUb2tlbklkIjoiYWRtaW4xLjY1NzU2NjQ1MTkwMjEwMjUiLCJqdGkiOiIyMWI2YWIyMi03NGRjLTExZWUtYjk2Mi0wMjQyYWMxMjAwMDIiLCJlbWFpbCI6ImFkbWluQGNsZWEuY29tIn0.LiA425M0Z-UFzXNDCkRaE7sPlig3X5IQJq9mq76-VziOJBl4EdgMow1zUPw6Ri69EjoHdSHflMhUA-QB_Ii6Bw")
    ResponseEntity<UserDTO> getUserById(@PathVariable("user_id") String userId);
}
