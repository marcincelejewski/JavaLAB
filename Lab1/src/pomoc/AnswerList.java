/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pomoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcin
 */
public class AnswerList {
    private List<String[]> answerList = new ArrayList();

    public List<String[]> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String[]> answerList) {
        this.answerList = answerList;
    }
   
    
    public AnswerList(String fileName)
    {
        FileReader fileReader = null;
        String text;
        try {
            fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((text=bufferedReader.readLine())!=null)
            {
                String[] answerSheet = text.split(";");
                answerList.add(answerSheet);
            }
            } catch (FileNotFoundException ex) {
            Logger.getLogger(AnswerList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AnswerList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(AnswerList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
