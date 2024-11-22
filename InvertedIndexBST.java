public class InvertedIndexBST {  
            BST <String,DocumentNode> invertedindexBST;   
            int count = 0;  
  
            public InvertedIndexBST() {  
            	invertedindexBST = new BST< String , DocumentNode> ();  
            }  
  
            public boolean addNew (int docID, String word)  
            {  
               if (invertedindexBST.empty())  
               {  
                   count ++;  
                   DocumentNode t = new DocumentNode(docID,word);    
                   invertedindexBST.insert(word, t);  
                   return true;  
               }  
               else  
               {  
                    if (invertedindexBST.find(word))  
                    {  
                    	DocumentNode t = this.invertedindexBST.retrieve();  
                        t.docID= docID;  
                        invertedindexBST.update(t);  
                        return false;  
                          
                    }  
                      
                   count ++;  
                   DocumentNode t = new DocumentNode (docID,word );   
                   invertedindexBST.insert(word, t);  
                    return true;  
           }  
        }  
  
        public boolean found(String word)  
        {  
               return invertedindexBST.find(word);  
        }  
          
        public void printDocument()  
        {  
            invertedindexBST.Traverse();  
        }  
}

