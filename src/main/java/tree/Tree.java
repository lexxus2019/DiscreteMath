package tree;

import java.util.List;
import java.util.Map;

public class Tree extends Node {

    public Tree(String data) throws Exception {
        super(data);
    }

    @Override
    public void resetValues() throws Exception {
        super.resetValues();
    }

    @Override
    public List<Node> linearizeChields() throws Exception {
        return super.linearizeChields();
    }

    public void setValues(Map<String, Integer> inputData) throws Exception {
        var tails = getTails();

        for (var item : tails) {
            var key = item.getData();
            if (inputData.containsKey(key)) {
                item.setValue(inputData.get(key));
            }
        }
    }

}
