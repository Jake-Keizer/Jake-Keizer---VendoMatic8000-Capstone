package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MachineTest {

    Machine sut;

    @Before
    public void freshTest(){
        Machine sut = new Machine();
    }

    @Test
    public void Print_To_Log_Prints_Correct_Entry(){
        String testEntry = "Sat Feb 12 11:50:26 EST 2022 GIVE CHANGE: $3.75 $0.00";


    }

}