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
public class Template {
private List<Question> questions = new ArrayList();
  
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
  
public Template(String nameFile)
{  
    try {
        FileReader fileReader = new FileReader(nameFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        String text;
        
        do 
        {
            Question question = new Question();
            text = bufferedReader.readLine();
            if(text == null)break;
            if(text.equals("Question")==true)
            {
                text = bufferedReader.readLine();
                question.setQuestion(text);
                for(int i=0;i<3;i++)
                {
                    text = bufferedReader.readLine();
                    if(text.equals("Answer")==true)
                    {
                        text = bufferedReader.readLine();
                        question.setAnswer(text,i);
                    }
                    else if(text.equals("xAnswer")==true)
                    {
                        text = bufferedReader.readLine();
                        question.setAnswer(text,i);
                        switch (i) {
                            case 0:
                                question.setTrueAnswer("A");
                                break;
                            case 1:
                                question.setTrueAnswer("B");
                                break;
                            default:
                                question.setTrueAnswer("C");
                                break;
                        }
                    }                   
                }
                questions.add(question);
            }          
            
        } while (true);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(Template.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
@Override
    public String toString()
{
    String s="";
    for(int i=0;i<questions.size();i++)
    {
        s+=Integer.toString(i+1)+". "+questions.get(i).toString()+"\n";
    }
    return s;
}
}