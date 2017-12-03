/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenariolauncher;

/**
 *
 * @author sydna
 */
public class State {
    private String action;
    private int data;
    public State(String action, int data) {
        this.action = action;
        this.data = data;
    }
    
    public State(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public int getData() {
        return data;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setData(int data) {
        this.data = data;
    }
    
}
