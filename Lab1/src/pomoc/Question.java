/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pomoc;


/**
 *
 * @author Marcin
 */
public class Question {
private String question;
private final String[] answer = new String[3];
private String trueAnswer;
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
     public void setAnswer(String answer, int index) {
        this.answer[index] = answer;
    }
     public String getAnswer(int index) {
        return this.answer[index];
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

   
@Override
public String toString()
{
    return question+"\nA: "+answer[0]+"\nB: "+answer[1]+"\nC: "+answer[2]+"\nPrawidłowa odpowiedź: "+trueAnswer;
    
}
   

}
