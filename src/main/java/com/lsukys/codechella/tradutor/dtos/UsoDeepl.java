package com.lsukys.codechella.tradutor.dtos;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record UsoDeepl(long character_count, long character_limit) {

    public String getUsage() {
        String usage =
                  "\tUsado .......: " + character_count + ".\n"
                + "\tPermitido ...: " + character_limit  + ".\n"
                + "\tRestante ....: " + (character_limit - character_count)  + ".\n" ;
        return usage;
    }

}
