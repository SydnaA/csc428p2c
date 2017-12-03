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
    private int index;

    public State(String action, int data) {
        this.action = action;
        this.data = data;
        this.index = -1;
    }
    
    public State(String action, int data, int index) {
        this.action = action;
        this.data = data;
        this.index = index;
    }

    public int getLocIndex() {
        return index;
    }

    public State(String action) {
        this.action = action;
        this.data = -1;
        this.index = -1;
    }

    @Override
    public String toString() {
        return "action: " + action + (data == -1 ? "" : "  " + "data: " + data) + (index == -1 ? "" : "  " + "index: " + index);
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
