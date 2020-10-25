<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">
        <title>JSP Page</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <!-- div de centrage -->
        <div>
            
            <!-- fichiers inclus -->
            <jsp:include page="../haut.jsp"/>
            <jsp:include page="../navigation.jsp"/>
      
   
        
        <div id="main">
            <h3>Gestion des tags pour la news ${news.titre}</h3>
            
            
            <form action="news" method="post">
                <input type="hidden" name="action" value="${requestScope.action}"/>
                <input type="hidden" name="id" value="${requestScope.news.idNews}"/>
                
                
                <!-- BLOC TAGS -->
            <style>
                #BLOC-TAGS div{display:inline-block; width: 40%;}
            </style>
            <div id="BLOC-TAGS">

                <div>
                <p>ALL TAGS</p>
                <p>
                    <select id="liste1" name="1" size="5" multiple>  
                        <c:forEach var="tag" items="${requestScope.allTagsJSP}" >
                            <option value="${tag.idTag}">${tag.libelle}</option>   
                        </c:forEach>    
                    </select>   
                </p>
                </div>
                
                <div>
                <p>TAGS FOR THE NEWS</p>
                <p>
                    <select id="liste2" name="liste2" size="5" multiple>   
                        <c:forEach var="tag" items="${requestScope.listeTagsForZeNewsJSP}">    
                            <option value="${tag.idTag}">${tag.libelle}</option>        
                        </c:forEach>    
                    </select>    
                </p>
                </div>

            </div> <!-- FIN BLOC TAGS -->   
            
            <p class="submit"><input onClick="selectThemAll()" type="image" src="MEDIAS/GO.jpg" /></p>
                    
            </form>
            
            

        </div>
        </div>
        
            <script>
                
                // Raccourcis vers les composants web
                let liste1 = document.getElementById("liste1");
                let liste2 = document.getElementById("liste2");
                
                // Dom Events
                liste1.addEventListener("change", processListe1);
                liste2.addEventListener("change", processListe2);
                
                function processListe1(){
                    
                    let indexSelectionne = liste1.selectedIndex;
                    let optionSelectionnee = liste1.options[indexSelectionne];
                    
                    // Retirer l 'option du select 1
                    liste1.removeChild(optionSelectionnee);
                    
                    // CREA ELEM OPTION
                    let newOption = document.createElement("option");
                    
                    // On gère l'attribut 'value' et sa valeur
                    newOption.setAttribute("value", optionSelectionnee.value);
                    // OU BIEN (on part du principe que l'option A un attribut 'value' )
                   // newOption.value = optionSelectionnee.value;
                   
                   // On gère son attribut 'text' et sa valeur
                    let labelText = document.createTextNode(optionSelectionnee.text);
                    newOption.appendChild(labelText);
                    // OU BIEN (on part du principe que l'option A un attribut 'text')
                   //newOption.text = optionSelectionnee.text;
                   
                    // Ajouter l'option crée au select 2
                    liste2.appendChild(newOption);
                    
                }
                
                //------------------------------------------
                function processListe2(){
                    
                    //console.log("2 OPTION HAS BEEN CHANGED !");

                    let indexSelectionne = liste2.selectedIndex;
                    let optionSelectionnee = liste2.options[indexSelectionne];
                    //console.log("INDEX Liste 1 ===> " + indexSelectionne);
                    //console.log("INDEX Liste 1 ===> " + optionSelectionnee);
                    
                    // Retirer l 'option du select 1
                    liste2.removeChild(optionSelectionnee);
                    
                    // CREA ELEM OPTION
                    // Céer un une 'vraie' option (dom)
                    let newOption = document.createElement("option");
                    
                    // On gère l'attribut 'value' et sa valeur
                    newOption.setAttribute("value", optionSelectionnee.value);
                   // newOption.value = optionSelectionnee.value;
                   
                   // On gère son attribut 'text' et sa valeur
                    let labelText = document.createTextNode(optionSelectionnee.text);
                    newOption.appendChild(labelText);
                   //newOption.text = optionSelectionnee.text;
                   
                    // Ajouter l'option crée au select 2
                    liste1.appendChild(newOption);
                    console.log(newOption.value);
                    
                }
                
                function selectThemAll(){
                    
                for(let i=0; i<document.getElementById("liste2").options.length; i++){
                document.getElementById("liste2").options[i].selected = true; 
                }
                    
                }
                
            </script>
        
    </body>
</html>


