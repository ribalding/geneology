import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App{
  public static void main(String[] args){
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addUser", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      boolean confirmNewUser = false;
      boolean failedNewUser = false;
      String userFullName = request.queryParams("userFullName");
      String userPassword = request.queryParams("userPassword");
      String confirmPassword = request.queryParams("confirmPassword");
      boolean validation = User.validate(userFullName, userPassword, confirmPassword);
      if (validation == true){
        User newUser = new User(userFullName, userPassword);
        newUser.save();
        confirmNewUser = true;
        model.put("confirmNewUser", confirmNewUser);
      } else if (validation == false){
        failedNewUser = true;
        model.put("failedNewUser", failedNewUser);
      }
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  get("/familyForm/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    User newUser = User.find(Integer.parseInt(request.params("id")));
    model.put("user", newUser);
    model.put("template", "templates/form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/accountHome", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    String userName = request.queryParams("userName");
    String userPassword = request.queryParams("userPassword");
    User newUser = User.findByName(userName);
    if (newUser.getPassword().equals(userPassword)){
      model.put("user", newUser);
      model.put("treeExists", newUser.getTreeExists());
      model.put("template", "templates/accountHome.vtl");
      System.out.println(newUser.getTreeExists());
    } else {
      model.put("template", "templates/index.vtl");
      boolean failedLogin = true;
      model.put("failedLogin", failedLogin);
    }
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/accountHome/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    User newUser = User.find(Integer.parseInt(request.params("id")));
    model.put("user", newUser);
    model.put("template", "templates/accountHome.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/viewTree/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    User newUser = User.find(Integer.parseInt(request.params("id")));

    Relative father = newUser.getRelative(1);
    model.put("father", father);

    Relative mother = newUser.getRelative(10);
    model.put("mother", mother);

    Relative paternalGrandfather = newUser.getRelative(2);
    model.put("paternalGrandfather", paternalGrandfather);

    Relative paternalGrandmother = newUser.getRelative(3);
    model.put("paternalGrandmother", paternalGrandmother);

    Relative fathersSibling1= newUser.getRelative(4);
    model.put("fathersSibling1", fathersSibling1);

    Relative fathersSibling2 = newUser.getRelative(5);
    model.put("fathersSibling2", fathersSibling2);

    Relative fathersSibling1Kid1 = newUser.getRelative(6);
    model.put("fathersSibling1Kid1", fathersSibling1Kid1);

    Relative fathersSibling1Kid2 = newUser.getRelative(7);
    model.put("fathersSibling1Kid2", fathersSibling1Kid2);

    Relative fathersSibling2Kid1 = newUser.getRelative(8);
    model.put("fathersSibling2Kid1", fathersSibling2Kid1);

    Relative fathersSibling2Kid2 = newUser.getRelative(9);
    model.put("fathersSibling2Kid2", fathersSibling2Kid2);

    Relative userSibling1 = newUser.getRelative(19);
    model.put("userSibling1", userSibling1);

    Relative userSibling2 = newUser.getRelative(20);
    model.put("userSibling2", userSibling2);

    Relative maternalGrandfather = newUser.getRelative(11);
    model.put("maternalGrandfather", maternalGrandfather);

    Relative maternalGrandmother = newUser.getRelative(12);
    model.put("maternalGrandmother", maternalGrandmother);

    Relative mothersSibling1 = newUser.getRelative(13);
    model.put("mothersSibling1", mothersSibling1);

    Relative mothersSibling2 = newUser.getRelative(14);
    model.put("mothersSibling2", mothersSibling2);

    Relative mothersSibling1Kid1 = newUser.getRelative(15);
    model.put("mothersSibling1Kid1", mothersSibling1Kid1);

    Relative mothersSibling1Kid2 = newUser.getRelative(16);
    model.put("mothersSibling1Kid2", mothersSibling1Kid2);

    Relative mothersSibling2Kid1 = newUser.getRelative(17);
    model.put("mothersSibling2Kid1", mothersSibling2Kid1);

    Relative mothersSibling2Kid2 = newUser.getRelative(18);
    model.put("mothersSibling2Kid2", mothersSibling2Kid2);

    model.put("userName", newUser);
    model.put("template", "templates/tree.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/addTree/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    User newUser = User.find(Integer.parseInt(request.params("id")));

    String father = request.queryParams("father");
    String fathersFather = request.queryParams("fathersFather");
    String fathersMother = request.queryParams("fathersMother");
    String fathersSibling1 = request.queryParams("fathersSibling1");
    String fathersSibling2 = request.queryParams("fathersSibling2");
    String fathersSibling1Kid1 = request.queryParams("fathersSibling1Kid1");
    String fathersSibling1Kid2 = request.queryParams("fathersSibling1Kid2");
    String fathersSibling2kid1 = request.queryParams("fathersSibling2kid1");
    String fathersSibling2kid2 = request.queryParams("fathersSibling2kid2");
    String mother = request.queryParams("mother");
    String mothersFather = request.queryParams("mothersFather");
    String mothersMother = request.queryParams("mothersMother");
    String mothersSibling1 = request.queryParams("mothersSibling1");
    String mothersSibling2 = request.queryParams("mothersSibling2");
    String mothersSibling1Kid1 = request.queryParams("mothersSibling1Kid1");
    String mothersSibling1Kid2 = request.queryParams("mothersSibling1Kid2");
    String mothersSibling2kid1 = request.queryParams("mothersSibling2kid1");
    String mothersSibling2kid2 = request.queryParams("mothersSibling2kid2");
    String userSibling1 = request.queryParams("userSibling1");
    String userSibling2 = request.queryParams("userSibling2");

    Relative newFather = new Relative(father, "Father", newUser.getId(), 1);
    newFather.save();
    Relative newPaternalGrandfather = new Relative(fathersFather, "Paternal Grandfather", newUser.getId(), 2);
    newPaternalGrandfather.save();
    Relative newPaternalGrandmother = new Relative(fathersMother, "Paternal Grandmother", newUser.getId(), 3);
    newPaternalGrandmother.save();
    Relative newFathersSibling1 = new Relative(fathersSibling1, "Father Sibling 1", newUser.getId(), 4);
    newFathersSibling1.save();
    Relative newFathersSibling2 = new Relative(fathersSibling2, "Father Sibling 2", newUser.getId(), 5);
    newFathersSibling2.save();
    Relative newFathersSibling1Kid1 = new Relative(fathersSibling1Kid1, "Father Sibling 1 Child 1", newUser.getId(), 6);
    newFathersSibling1Kid1.save();
    Relative newFathersSibling1Kid2 = new Relative(fathersSibling1Kid2, "Father Sibling 1 Child 2", newUser.getId(), 7);
    newFathersSibling1Kid2.save();
    Relative newFathersSibling2Kid1 = new Relative(fathersSibling2kid1, "Father Sibling 2 Child 1", newUser.getId(), 8);
    newFathersSibling2Kid1.save();
    Relative newFathersSibling2Kid2 = new Relative(fathersSibling2kid2, "Father Sibling 2 Child 2", newUser.getId(), 9);
    newFathersSibling2Kid2.save();

    Relative newMother = new Relative(mother, "Mother", newUser.getId(), 10);
    newMother.save();
    Relative newMaternalGrandfather = new Relative(mothersFather, "Maternal Grandfather", newUser.getId(), 11);
    newMaternalGrandfather.save();
    Relative newMaternalGrandmother = new Relative(mothersMother, "Maternal Grandmother", newUser.getId(), 12);
    newMaternalGrandmother.save();
    Relative newMothersSibling1 = new Relative(mothersSibling1, "Mother Sibling 1", newUser.getId(), 13);
    newMothersSibling1.save();
    Relative newMothersSibling2 = new Relative(mothersSibling2, "Mother Sibling 2", newUser.getId(), 14);
    newMothersSibling2.save();
    Relative newMothersSibling1Kid1 = new Relative(mothersSibling1Kid1, "Mother Sibling 1 Child 1", newUser.getId(), 15);
    newMothersSibling1Kid1.save();
    Relative newMothersSibling1Kid2 = new Relative(mothersSibling1Kid2, "Mother Sibling 1 Child 2", newUser.getId(), 16);
    newMothersSibling1Kid2.save();
    Relative newMothersSibling2Kid1 = new Relative(mothersSibling2kid1, "Mother Sibling 2 Child 1", newUser.getId(), 17);
    newMothersSibling2Kid1.save();
    Relative newMothersSibling2Kid2 = new Relative(mothersSibling2kid2, "Mother Sibling 2 Child 2", newUser.getId(), 18);
    newMothersSibling2Kid2.save();

    Relative newUserSibling1 = new Relative(userSibling1, "Sibling 1", newUser.getId(), 19);
    newUserSibling1.save();
    Relative newUserSibling2 = new Relative(userSibling2, "Sibling 2", newUser.getId(), 20);
    newUserSibling2.save();

    newUser.treeNowExists();

    response.redirect("/viewTree/" + newUser.getId());
    return null;
  });

  get("familyForm/:id/update", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    User newUser = User.find(Integer.parseInt(request.params("id")));

    Relative father = newUser.getRelative(1);
    model.put("father", father);

    Relative mother = newUser.getRelative(10);
    model.put("mother", mother);

    Relative paternalGrandfather = newUser.getRelative(2);
    model.put("paternalGrandfather", paternalGrandfather);

    Relative paternalGrandmother = newUser.getRelative(3);
    model.put("paternalGrandmother", paternalGrandmother);

    Relative fathersSibling1= newUser.getRelative(4);
    model.put("fathersSibling1", fathersSibling1);

    Relative fathersSibling2 = newUser.getRelative(5);
    model.put("fathersSibling2", fathersSibling2);

    Relative fathersSibling1Kid1 = newUser.getRelative(6);
    model.put("fathersSibling1Kid1", fathersSibling1Kid1);

    Relative fathersSibling1Kid2 = newUser.getRelative(7);
    model.put("fathersSibling1Kid2", fathersSibling1Kid2);

    Relative fathersSibling2Kid1 = newUser.getRelative(8);
    model.put("fathersSibling2Kid1", fathersSibling2Kid1);

    Relative fathersSibling2Kid2 = newUser.getRelative(9);
    model.put("fathersSibling2Kid2", fathersSibling2Kid2);

    Relative userSibling1 = newUser.getRelative(19);
    model.put("userSibling1", userSibling1);

    Relative userSibling2 = newUser.getRelative(20);
    model.put("userSibling2", userSibling2);

    Relative maternalGrandfather = newUser.getRelative(11);
    model.put("maternalGrandfather", maternalGrandfather);

    Relative maternalGrandmother = newUser.getRelative(12);
    model.put("maternalGrandmother", maternalGrandmother);

    Relative mothersSibling1 = newUser.getRelative(13);
    model.put("mothersSibling1", mothersSibling1);

    Relative mothersSibling2 = newUser.getRelative(14);
    model.put("mothersSibling2", mothersSibling2);

    Relative mothersSibling1Kid1 = newUser.getRelative(15);
    model.put("mothersSibling1Kid1", mothersSibling1Kid1);

    Relative mothersSibling1Kid2 = newUser.getRelative(16);
    model.put("mothersSibling1Kid2", mothersSibling1Kid2);

    Relative mothersSibling2Kid1 = newUser.getRelative(17);
    model.put("mothersSibling2Kid1", mothersSibling2Kid1);

    Relative mothersSibling2Kid2 = newUser.getRelative(18);
    model.put("mothersSibling2Kid2", mothersSibling2Kid2);

    model.put("user", newUser);

    model.put("template", "templates/updateTree.vtl");
    return new ModelAndView(model,layout);
  }, new VelocityTemplateEngine());

  post("familyForm/:id/submitUpdate", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    User newUser = User.find(Integer.parseInt(request.params("id")));

    String father = request.queryParams("father");
    String fathersFather = request.queryParams("fathersFather");
    String fathersMother = request.queryParams("fathersMother");
    String fathersSibling1 = request.queryParams("fathersSibling1");
    String fathersSibling2 = request.queryParams("fathersSibling2");
    String fathersSibling1Kid1 = request.queryParams("fathersSibling1Kid1");
    String fathersSibling1Kid2 = request.queryParams("fathersSibling1Kid2");
    String fathersSibling2kid1 = request.queryParams("fathersSibling2kid1");
    String fathersSibling2kid2 = request.queryParams("fathersSibling2kid2");
    String mother = request.queryParams("mother");
    String mothersFather = request.queryParams("mothersFather");
    String mothersMother = request.queryParams("mothersMother");
    String mothersSibling1 = request.queryParams("mothersSibling1");
    String mothersSibling2 = request.queryParams("mothersSibling2");
    String mothersSibling1Kid1 = request.queryParams("mothersSibling1Kid1");
    String mothersSibling1Kid2 = request.queryParams("mothersSibling1Kid2");
    String mothersSibling2kid1 = request.queryParams("mothersSibling2kid1");
    String mothersSibling2kid2 = request.queryParams("mothersSibling2kid2");
    String userSibling1 = request.queryParams("userSibling1");
    String userSibling2 = request.queryParams("userSibling2");

    Relative currentFather = newUser.getRelative(1);
    currentFather.update(father);

    Relative currentMother= newUser.getRelative(10);
    currentMother.update(mother);

    Relative currentPaternalGrandfather = newUser.getRelative(2);
    currentPaternalGrandfather.update(fathersFather);

    Relative currentPaternalGrandmother = newUser.getRelative(3);
    currentPaternalGrandmother.update(fathersMother);

    Relative currentFathersSibling1 = newUser.getRelative(4);
    currentFathersSibling1.update(fathersSibling1);

    Relative currentFathersSibling2 = newUser.getRelative(5);
    currentFathersSibling2.update(fathersSibling2);

    Relative currentFathersSibling1Kid1 = newUser.getRelative(6);
    currentFathersSibling1Kid1.update(fathersSibling1Kid1);

    Relative currentFathersSibling1Kid2 = newUser.getRelative(7);
    currentFathersSibling1Kid2.update(fathersSibling1Kid2);

    Relative currentFathersSibling2Kid1 = newUser.getRelative(8);
    currentFathersSibling2Kid1.update(fathersSibling2kid1);

    Relative currentFathersSibling2Kid2 = newUser.getRelative(9);
    currentFathersSibling2Kid2.update(fathersSibling2kid2);

    Relative currentMaternalGrandfather = newUser.getRelative(11);
    currentMaternalGrandfather.update(mothersFather);

    Relative currentMaternalGrandmother = newUser.getRelative(12);
    currentMaternalGrandmother.update(mothersMother);

    Relative currentMothersSibling1 = newUser.getRelative(13);
    currentMothersSibling1.update(mothersSibling1);

    Relative currentMothersSibling2 = newUser.getRelative(14);
    currentMothersSibling2.update(mothersSibling2);

    Relative currentMothersSibling1Kid1 = newUser.getRelative(15);
    currentMothersSibling1Kid1.update(mothersSibling1Kid1);

    Relative currentMothersSibling1Kid2 = newUser.getRelative(16);
    currentMothersSibling1Kid2.update(mothersSibling1Kid2);

    Relative currentMothersSibling2Kid1 = newUser.getRelative(17);
    currentMothersSibling2Kid1.update(mothersSibling2kid1);

    Relative currentMothersSibling2Kid2 = newUser.getRelative(18);
    currentMothersSibling2Kid2.update(mothersSibling2kid2);

    Relative currentUserSibling1 = newUser.getRelative(19);
    currentUserSibling1.update(userSibling1);

    Relative currentUserSibling2 = newUser.getRelative(20);
    currentUserSibling2.update(userSibling2);


    response.redirect("/viewTree/" + newUser.getId());
    return null;
  });

  get("/addImg/:relativeId", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Relative newRelative = Relative.find(Integer.parseInt(request.params("relativeId")));
    model.put("relative", newRelative);
    model.put("template", "templates/addImg.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("submitImg/:relativeId", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    Relative newRelative = Relative.find(Integer.parseInt(request.params("relativeId")));
    String imagePath = request.queryParams("image_input");
    newRelative.addImg(imagePath);
    User newUser = User.find(newRelative.getUserId());
    response.redirect("/viewTree/" + newUser.getId());
    return null;
  });

  get("/addUserImg/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    User newUser = User.find(Integer.parseInt(request.params("id")));
    model.put("user", newUser);
    model.put("template", "templates/addUserImg.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("submitUserImg/:id", (request, response) -> {
    Map<String, Object> model = new HashMap<String, Object>();
    User newUser = User.find(Integer.parseInt(request.params("id")));
    String imagePath = request.queryParams("image_input");
    newUser.addImg(imagePath);
    response.redirect("/viewTree/" + newUser.getId());
    return null;
  });
  }
}
