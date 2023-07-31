package com.calc;

public class Command {
    private final CommandType type;

    public Command(CommandType type){
        this.type = type;
    }

    public CommandType getType(){
        return type;
    }
}
