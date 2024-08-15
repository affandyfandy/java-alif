package com.fsoft.lecture16.openfeign.decoder;

import com.fsoft.lecture16.openfeign.exception.BadRequestException;
import com.fsoft.lecture16.openfeign.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BadRequestException();
            case 404:
                return new NotFoundException();
            default:
                return new Exception(response.reason());
        }
    }
}
