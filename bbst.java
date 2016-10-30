/**
 * Created by Ankit on 16-Mar-16.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class bbst {


    //Initializing red and black colors for nodes in the tree as boolean values. Setting black as false and red as true.
    private static final boolean BlackValue = false;
    private static final boolean RedValue = true;


    /*Class CreateNode used to create tree nodes initially in the balanced binary search tree, which are then converted 
    * to Red and Black Trees*/
    public class CreateNode{
        //Initializing id and count of nodes and the corresponding values are assigned to them in the Constructor CreateNode
        int IdValue;
        int CountValue;
        /*Every node will have a parentNode, leftC (leftChild) and rightC (rightChild). These are initialized below.
        * Boolean color is initialized here to which the RedValue (true) or BlueValue (false) is assigned*/
        CreateNode parentNode, leftC, rightC;
        boolean color;
        CreateNode(int id, int count){
            this.IdValue = id;
            this.CountValue = count;
            this.color = BlackValue;
            //Initializing parentNode, leftC and rightC as null
            this.parentNode = null;
            this.leftC = null;
            this.rightC = null;
        }
    }


    //Initializing the rootNode of the balanced binary search tree which is later converted to Red and Black tree
    static CreateNode rootNode;


    /*Function to create a Red Black tree, where initially we create a Balanced Binary Search tree recursively and then
    * color the last level of the tree as Red (RedValue i.e. true) to create the Red and Black tree*/
    public CreateNode RedBlackBST(int[] ArrayOfId, int[] ArrayOfCount, int front, int back, int HeightOfTree){
        if(front > back){
            return null;
        }
        //Finds the middle element
        int middleElement = (front + back)/2;
        CreateNode NodeRB = new CreateNode(ArrayOfId[middleElement], ArrayOfCount[middleElement]);
        NodeRB.leftC = RedBlackBST(ArrayOfId, ArrayOfCount, front, middleElement - 1, HeightOfTree - 1);
        //If NodeRB has a left child, it will set the left child's parent as NodeRB
        if(NodeRB.leftC != null){
            NodeRB.leftC.parentNode = NodeRB;
        }
        NodeRB.rightC = RedBlackBST(ArrayOfId, ArrayOfCount, middleElement + 1, back, HeightOfTree - 1);
        //If NodeRB has a right child, it will set the right child's parent as NodeRB
        if(NodeRB.rightC != null){
            NodeRB.rightC.parentNode = NodeRB;
        }
        //To create the Red Black Tree, set color of all the nodes at level 0 to Red (RedValue - true)
        if(HeightOfTree == 0){
            NodeRB.color = RedValue;
        }
        return NodeRB;
    }


    //RBT_Min finds node with the minimum id value in a tree or subtree
    public CreateNode RBT_Min(CreateNode NodeRB){
        while (NodeRB.leftC != null){
            NodeRB = NodeRB.leftC;
        }
        return NodeRB;
    }


    //RBT_Max finds node with the maximum id value in a tree or subtree
    public CreateNode RBT_Max(CreateNode NodeRB){
        while (NodeRB.rightC != null){
            NodeRB = NodeRB.rightC;
        }
        return NodeRB;
    }


    //This function returns the predecessor of a node
    CreateNode Predecessor(CreateNode NodeRB){
        //If a node's left child is not null, it's predecessor is the node with the maximum id value in its left subtree
        if(NodeRB.leftC != null){
            CreateNode temporaryNodeRB = RBT_Max(NodeRB.leftC);
            return temporaryNodeRB;
        }
        CreateNode parentOfNodeRB = NodeRB.parentNode;
        while (parentOfNodeRB != null && NodeRB == parentOfNodeRB.leftC){
            NodeRB = parentOfNodeRB;
            parentOfNodeRB = parentOfNodeRB.parentNode;
        }
        return  parentOfNodeRB;
    }


    /*The previous function takes id value of a node. Searches for the node initially. If the node is found, returns
    * the node, else it returns the node with id value closest to the given id value*/
    void previous(int nodeId) {
        CreateNode NodeRb = search(rootNode, nodeId);
        //If the node value is less than the given value, then the node returned by search function is the predecessor
        if(NodeRb != null && NodeRb.IdValue < nodeId){
            System.out.println(NodeRb.IdValue + " " + NodeRb.CountValue);
        }
        /*Else if the node is null, we return "0 0". Otherwise we call the predecessor function and look for the
        * predecessor of the node*/
        else{
            if(NodeRb == null){
                System.out.println("0 0");
            }
            else {
                CreateNode predecessorOfNodeRB = Predecessor(NodeRb);
                if (predecessorOfNodeRB != null) {
                    System.out.println(Predecessor(NodeRb).IdValue + " " + Predecessor(NodeRb).CountValue);
                }
                else {
                    System.out.println("0 0");
                }
            }
        }
    }


    /*The search function returns the node closest in id value to the id provided. The node provided to function is the
    * root node. If node provided is null, then we return the value as null.*/
    public CreateNode search(CreateNode NodeRB, int nodeId){
        if(NodeRB == null){
            return null;
        }
        CreateNode temporaryNodeRB = NodeRB;
        int minDiff = Integer.MAX_VALUE;
        while(NodeRB != null){
            //When the node's id value equals the given id, we return the node
            if(NodeRB.IdValue == nodeId){
                return NodeRB;
            }
            /*Else we calculate the difference between the given id and node's id. And update the minimum difference
            * set above if necessary, while also updating the node which holds the minimum difference value. If given
            * id is greater than node's id value, we set node as node's right child else set node as node's left child.
            * Finally, return the temporary node as the node with the closest id value to the given id.*/
            else {
                int diff = Math.abs(nodeId - NodeRB.IdValue);
                if (diff < minDiff) {
                    minDiff = diff;
                    temporaryNodeRB = NodeRB;
                }
                if (nodeId > NodeRB.IdValue) {
                    NodeRB = NodeRB.rightC;
                }
                else {
                    NodeRB = NodeRB.leftC;
                }
            }
        }
        return temporaryNodeRB;
    }


    //This function returns the successor of a node
    CreateNode Successor(CreateNode NodeRB){
        //If the node's right child is not null, it's successor is the node with the minimum id value in its right subtree
        if(NodeRB.rightC != null){
            CreateNode temporaryNodeRB = RBT_Min(NodeRB.rightC);
            return temporaryNodeRB;
        }
        CreateNode parentOfNodeRB = NodeRB.parentNode;
        while(parentOfNodeRB != null && NodeRB == parentOfNodeRB.rightC){
            NodeRB = parentOfNodeRB;
            parentOfNodeRB = parentOfNodeRB.parentNode;
        }
        return parentOfNodeRB;
    }


    /*The next function takes id value of a node. Searches for the node initially. If the node is found, returns
    * the node, else it returns the node with id value closest to the given id value. It uses the same search function
    * that previous function does.*/
    void next(int nodeId){
        CreateNode NodeRB = search(rootNode, nodeId);
        //If the node value is greater than the given value, then the node returned by search function is the successor
        if(NodeRB != null && NodeRB.IdValue > nodeId){
            System.out.println(NodeRB.IdValue + " " + NodeRB.CountValue);
        }
        /*Else if the node is null, we return "0 0". Otherwise we call the successor function and look for the
        * successor of the node*/
        else{
            CreateNode temporaryNodeRB = Successor(NodeRB);
            if(temporaryNodeRB != null){
                System.out.println(temporaryNodeRB.IdValue + " " + temporaryNodeRB.CountValue);
            }
            else{
                System.out.println("0 0");
            }
        }
    }


    /*The inrange function implements the requirement of returning total count of nodes whose value lies between two nodes
    * inclusive. The node passed to the function as argument is the root node.*/
    int inrange(CreateNode NodeRB, int uId, int vId){
        //If the root node is null, return 0.
        if(NodeRB == null){
            return 0;
        }
        /*If the root node is not null and value of 1st node is equal to the value of 2nd node and both values are equal
        * to the value of the root node, return root node's count.*/
        if(NodeRB.IdValue == vId && NodeRB.IdValue == uId){
            return NodeRB.CountValue;
        }
        //If none of the above conditions are satisfied, we call the function recursively and return the total count
        if(NodeRB.IdValue <= vId && NodeRB.IdValue >= uId){
            return NodeRB.CountValue + inrange(NodeRB.leftC, uId, vId) + inrange(NodeRB.rightC, uId, vId);
        }
        else if(NodeRB.IdValue < uId){
            return inrange(NodeRB.rightC, uId, vId);
        }
        else{
            return inrange(NodeRB.leftC, uId, vId);
        }
    }


    /*The count function implements the required functionality where, when given an id value of a node, it's count needs
    * to be returned.*/
    int count(int searchVal){
        CreateNode NodeRB = rootNode;
        //A simple traversal of the red black tree from root node will do the trick. If node is not found, we return 0.
        while (NodeRB != null && searchVal != NodeRB.IdValue){
            if(searchVal < NodeRB.IdValue){
                NodeRB = NodeRB.leftC;
            }
            else{
                NodeRB = NodeRB.rightC;
            }
        }
        if(NodeRB == null){
            return 0;
        }
        else{
            return NodeRB.CountValue;
        }
    }


    /*The left_rotate function takes care of the left rotations required to be performed after insertions or deletions
    * are done on a red black tree.*/
    void left_Rotate(CreateNode NodeRB){
        CreateNode rightCOfNodeRB = NodeRB.rightC;
        NodeRB.rightC = rightCOfNodeRB.leftC;
        if (rightCOfNodeRB.leftC != null){
            rightCOfNodeRB.leftC.parentNode = NodeRB;
        }
        rightCOfNodeRB.parentNode = NodeRB.parentNode;
        if(NodeRB.parentNode == null){
            rootNode = rightCOfNodeRB;
        }
        else if(NodeRB == NodeRB.parentNode.leftC){
            NodeRB.parentNode.leftC = rightCOfNodeRB;
        }
        else{
            NodeRB.parentNode.rightC = rightCOfNodeRB;
        }
        rightCOfNodeRB.leftC = NodeRB;
        NodeRB.parentNode = rightCOfNodeRB;
    }


    /*The right_rotate function takes care of the right rotations to be performed after insertions or deletions are
    * done on a red black tree*/
    void right_Rotate(CreateNode NodeRB){
        CreateNode leftCOfNodeRB;
        leftCOfNodeRB = NodeRB.leftC;
        NodeRB.leftC = leftCOfNodeRB.rightC;
        if(leftCOfNodeRB.rightC != null){
            leftCOfNodeRB.rightC.parentNode = NodeRB;
        }
        leftCOfNodeRB.parentNode = NodeRB.parentNode;
        if(NodeRB.parentNode == null){
            rootNode = leftCOfNodeRB;
        }
        else if (NodeRB == NodeRB.parentNode.rightC){
            NodeRB.parentNode.rightC = leftCOfNodeRB;
        }
        else{
            NodeRB.parentNode.leftC = leftCOfNodeRB;
        }
        leftCOfNodeRB.rightC = NodeRB;
        NodeRB.parentNode = leftCOfNodeRB;
    }


    //The TreeTransplant function implements the changes necessary to maintain the red black tree properties after deletion of node
    void TreeTransplant(CreateNode u, CreateNode v){
        if(u.parentNode == null){
            rootNode = v;
        }
        else if(u == u.parentNode.leftC){
            u.parentNode.leftC = v;
        }
        else
            u.parentNode.rightC = v;
        if(v != null) {
            v.parentNode = u.parentNode;
        }
    }


    /*When the count value of a node after reduction goes to less than or equal to 0, the node is deleted. deleteNode function
    * takes care of this functionality. The initial deleting of node is done by this function and then the changes required
    * to maintain the red black tree properties are then done by delete_fixer function.*/
    void deleteNode(CreateNode NodeRB){
        CreateNode temporaryNodeRB = NodeRB;
        CreateNode tempNode;
        boolean originalYellow = temporaryNodeRB.color;
        if(NodeRB.leftC == null){
            tempNode = NodeRB.rightC;
            TreeTransplant(NodeRB, NodeRB.rightC);
        }
        else if(NodeRB.rightC == null){
            tempNode = NodeRB.leftC;
            TreeTransplant(NodeRB, NodeRB.leftC);
        }
        else{
            temporaryNodeRB = RBT_Min(NodeRB.rightC);
            originalYellow = temporaryNodeRB.color;
            tempNode = temporaryNodeRB.rightC;
            if(temporaryNodeRB.parentNode == NodeRB){
                if(tempNode != null) {
                    tempNode.parentNode = temporaryNodeRB;
                }
            }
            if(temporaryNodeRB.parentNode != NodeRB){
                TreeTransplant(temporaryNodeRB, temporaryNodeRB.rightC);
                temporaryNodeRB.rightC = NodeRB.rightC;
                temporaryNodeRB.rightC.parentNode = temporaryNodeRB;
            }
            TreeTransplant(NodeRB, temporaryNodeRB);
            temporaryNodeRB.leftC = NodeRB.leftC;
            temporaryNodeRB.leftC.parentNode = temporaryNodeRB;
            temporaryNodeRB.color = NodeRB.color;
        }
        if(!originalYellow && tempNode != null){
            deleteFixer(tempNode);
        }
    }


    //The deleteFixer function takes care of the necessary changes to the tree to maintain the red black tree properties after deletion
    void deleteFixer(CreateNode NodeRB){
        while (NodeRB != rootNode && !NodeRB.color){
            if(NodeRB == NodeRB.parentNode.leftC) {
                CreateNode temporaryNodeRB = NodeRB.parentNode.rightC;
                if (temporaryNodeRB.color) {
                    temporaryNodeRB.color = BlackValue;
                    NodeRB.parentNode.color = RedValue;
                    left_Rotate(NodeRB.parentNode);
                    temporaryNodeRB = NodeRB.parentNode.rightC;
                }
                if (!temporaryNodeRB.leftC.color && !temporaryNodeRB.rightC.color) {
                    temporaryNodeRB.color = RedValue;
                    NodeRB = NodeRB.parentNode;
                }
                else {
                    if (!temporaryNodeRB.rightC.color) {
                        temporaryNodeRB.leftC.color = BlackValue;
                        temporaryNodeRB.color = RedValue;
                        right_Rotate(temporaryNodeRB);
                        temporaryNodeRB = NodeRB.parentNode.rightC;
                    }
                    temporaryNodeRB.color = NodeRB.parentNode.color;
                    NodeRB.parentNode.color = BlackValue;
                    temporaryNodeRB.rightC.color = BlackValue;
                    left_Rotate(NodeRB.parentNode);
                    NodeRB = rootNode;
                }
            }
            else {
                CreateNode temporaryNodeRB = NodeRB.parentNode.leftC;
                if(temporaryNodeRB.color){
                    temporaryNodeRB.color = BlackValue;
                    NodeRB.parentNode.color = RedValue;
                    right_Rotate(NodeRB.parentNode);
                    temporaryNodeRB = NodeRB.parentNode.leftC;
                }
                if(!temporaryNodeRB.rightC.color && !temporaryNodeRB.leftC.color){
                    temporaryNodeRB.color = RedValue;
                    NodeRB = NodeRB.parentNode;
                }
                else {
                    if (!temporaryNodeRB.leftC.color) {
                        temporaryNodeRB.rightC.color = BlackValue;
                        temporaryNodeRB.color = RedValue;
                        left_Rotate(temporaryNodeRB);
                        temporaryNodeRB = NodeRB.parentNode.leftC;
                    }
                    temporaryNodeRB.color = NodeRB.parentNode.color;
                    NodeRB.parentNode.color = BlackValue;
                    temporaryNodeRB.leftC.color = BlackValue;
                    right_Rotate(NodeRB.parentNode);
                    NodeRB = rootNode;
                }
            }
        }
        NodeRB.color = BlackValue;
    }


    /*The reduce function takes care of the required reduce count value functionality. If the count value after reduction
    * is a positive integer, we return the count value. Else we delete the node and return count value as 0. delete function is
    * called when the count value goes to 0 or less after reduction.*/
    void reduce(int searchVal, int decreaseAmount){
        CreateNode NodeRB = rootNode;
        while (NodeRB != null && searchVal != NodeRB.IdValue){
            if(searchVal < NodeRB.IdValue){
                NodeRB = NodeRB.leftC;
            }
            else {
                NodeRB = NodeRB.rightC;
            }
        }
        if(NodeRB == null){
            System.out.println("0");
        }
        else{
            NodeRB.CountValue = NodeRB.CountValue - decreaseAmount;
            if(NodeRB.CountValue <= 0){
                System.out.println("0");
                deleteNode(NodeRB);
            }
            else{
                System.out.println(NodeRB.CountValue);
            }
        }
    }


    /*When the id of a node whose count is to be increased is not found, a node with the given id and count are inserted.
    * insertNode function takes care of this functionality. The initial inserting of node is done by this function and then
    * the changes required to maintain the red black tree properties are then done by Insert_Fix function.*/
    void insertNode(int nodeId, int nodeCount){
        CreateNode NodeRB = new CreateNode(nodeId, nodeCount);
        NodeRB.color = RedValue;
        CreateNode temporaryNodeY = null;
        CreateNode tempNodeX = rootNode;
        while (tempNodeX != null){
            temporaryNodeY = tempNodeX;
            if(NodeRB.IdValue < tempNodeX.IdValue){
                tempNodeX = tempNodeX.leftC;
            }
            else {
                tempNodeX = tempNodeX.rightC;
            }
        }
        NodeRB.parentNode = temporaryNodeY;
        if(temporaryNodeY == null){
            rootNode = NodeRB;
        }
        else if(NodeRB.IdValue < temporaryNodeY.IdValue){
            temporaryNodeY.leftC = NodeRB;
        }
        else {
            temporaryNodeY.rightC = NodeRB;
        }
        Insert_Fix(NodeRB);
    }


    //The Insert_Fix function takes care of the necessary changes to the tree to maintain the red black tree properties after insertion
    void Insert_Fix(CreateNode NodeRB) {
        while (NodeRB.parentNode.color) {
            if (NodeRB.parentNode == NodeRB.parentNode.parentNode.leftC) {
                CreateNode temporaryNodeY = NodeRB.parentNode.parentNode.rightC;
                if (temporaryNodeY.color) {
                    NodeRB.parentNode.color = BlackValue;
                    temporaryNodeY.color = BlackValue;
                    NodeRB.parentNode.parentNode.color = RedValue;
                    NodeRB = NodeRB.parentNode.parentNode;
                }
                else {
                    if (NodeRB == NodeRB.parentNode.rightC) {
                        NodeRB = NodeRB.parentNode;
                        left_Rotate(NodeRB);
                    }
                    NodeRB.parentNode.color = BlackValue;
                    NodeRB.parentNode.parentNode.color = RedValue;
                    right_Rotate(NodeRB.parentNode.parentNode);
                }
            }
            else {
                CreateNode temporaryNodeY = NodeRB.parentNode.parentNode.leftC;
                if (temporaryNodeY.color) {
                    NodeRB.parentNode.color = BlackValue;
                    temporaryNodeY.color = BlackValue;
                    NodeRB.parentNode.parentNode.color = RedValue;
                    NodeRB = NodeRB.parentNode.parentNode;
                }
                else {
                    if (NodeRB == NodeRB.parentNode.leftC) {
                        NodeRB = NodeRB.parentNode;
                        right_Rotate(NodeRB);
                    }
                    NodeRB.parentNode.color = BlackValue;
                    NodeRB.parentNode.parentNode.color = RedValue;
                    left_Rotate(NodeRB.parentNode.parentNode);
                }
            }
        }
        rootNode.color = BlackValue;
    }


    /*The increase function takes care of the required increase count value functionality. If the node whose count is to be
    * increased is not found, we insert the node with its id as the search value given and its count as the increase amount given.
    * insertNode function is called when the id value of node to be increased is not found.*/
    int increase(int searchVal, int increaseAmount){
        CreateNode NodeRB = rootNode;
        while (NodeRB != null && searchVal != NodeRB.IdValue){
            if(searchVal < NodeRB.IdValue){
                NodeRB = NodeRB.leftC;
            }
            else{
                NodeRB = NodeRB.rightC;
            }
        }
        if(NodeRB == null){
            insertNode(searchVal, increaseAmount);
            return increaseAmount;
        }
        else{
            NodeRB.CountValue = NodeRB.CountValue + increaseAmount;
            return  NodeRB.CountValue;
        }
    }


    //This is the main function
    public static void main(String[] args)throws IOException{
        try{
            //We are taking input using buffered reader and file as argument 0
            FileReader file = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(file);
            //Taking the number of nodes in the red black tree by reading the first number
            int num = Integer.parseInt(br.readLine());
            //Initializing string to save the file read
            String line;
            //Initializing two arrays, first to save the id values of nodes and the other to save the count values
            int[] ArrayOfId = new int[num];
            int[] ArrayOfCount = new int[num];
            //Initializing counter to 0, which is used to add numbers to the initially empty array
            int counter = 0;
            //The loop is run to read till the end of file is reached
            while((line = br.readLine()) != null){
                //As every line contains two numbers, the input takes is separated by space. We get two numbers stored in the str array below
                String[] str = line.split("\\s+");
                /*Save the two numbers in string and then parse through the strings to get the respective integer values. Store these integer
                * values in the respective arrays at counter location. Here, we are saving 1st number in array1 and 2nd in array2. Array1 is
                * the id value array and array2 is the count value array.*/
                String s = str[0];
                String t = str[1];
                int a = Integer.parseInt(s);
                int b = Integer.parseInt(t);
                ArrayOfId[counter] = a;
                ArrayOfCount[counter] = b;
                counter++;
            }
            /*Calculating the height of tree constructed using the formula given below. This height is used to create
            * the balanced binary search tree and also to color the last level of nodes to red*/
            bbst crb = new bbst();
            int HeightOfTree = (int)(Math.log(num)/Math.log(2));
            rootNode = crb.RedBlackBST(ArrayOfId, ArrayOfCount, 0, num - 1, HeightOfTree);
            //Taking input using buffered reader, which can be given through a file or directly from command line
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            //The output can be written in a file or can be displayed on command line
            String str;
            /*We create a switch case based on the first part of string received from command line
            * We decide which function to run using this switch case
            * System.exit(0) is called when "quit" is recognised in the input statement*/
            while((str = buff.readLine()) != null){
                String[] strArr = str.split("\\s+");
                switch (strArr[0]){
                    case "increase":
                        int incID = Integer.parseInt(strArr[1]);
                        int increaseAmount = Integer.parseInt(strArr[2]);
                        int countAfterInc = crb.increase(incID, increaseAmount);
                        System.out.println(countAfterInc);
                        break;

                    case "reduce":
                        int decID = Integer.parseInt(strArr[1]);
                        int decreaseAmount = Integer.parseInt(strArr[2]);
                        crb.reduce(decID, decreaseAmount);
                        break;

                    case "count":
                        int countID = Integer.parseInt(strArr[1]);
                        int countOfID = crb.count(countID);
                        System.out.println(countOfID);
                        break;

                    case "inrange":
                        int lowNum = Integer.parseInt(strArr[1]);
                        int highNum = Integer.parseInt(strArr[2]);
                        int totalCount = crb.inrange(rootNode, lowNum, highNum);
                        System.out.println(totalCount);
                        break;

                    case "next":
                        int nextID = Integer.parseInt(strArr[1]);
                        crb.next(nextID);
                        break;

                    case "previous":
                        int prevID = Integer.parseInt(strArr[1]);
                        crb.previous(prevID);
                        break;

                    case "quit":
                        System.exit(0);
                }
            }
        }
        catch(IOException err){
            err.printStackTrace();
        }
    }
}