import java.util.List;
import org.sql2o.*;

public class Relative{
  private int id;
  private String relative_name;
  private String relation;
  private int user_id;
  private int relation_type_id;
  private String img;

  public Relative(String relative_name, String relation, int user_id, int relation_type_id){
    this.relative_name = relative_name;
    this.relation = relation;
    this.user_id = user_id;
    this.relation_type_id = relation_type_id;
    this.img = "https://upload.wikimedia.org/wikipedia/commons/3/33/White_square_with_question_mark.png";
  }

  public String getRelativeName(){
    return this.relative_name;
  }

  public String getRelation(){
    return this.relation;
  }

  public int getUserId(){
    return this.user_id;
  }

  public int getRelationTypeId(){
    return this.relation_type_id;
  }

  public int getId(){
    return this.id;
  }

  public String getImg(){
    return this.img;
  }

  public void addImg(String image){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE relatives SET img = :img WHERE id = :id";
      con.createQuery(sql)
      .addParameter("img", image)
      .addParameter("id", this.id)
      .executeUpdate();
    }
    this.img = image;
  }

  public void save(){
    String sql = "INSERT INTO relatives (relative_name, relation, user_id, relation_type_id, img) VALUES (:relative_name, :relation, :user_id, :relation_type_id, :img);";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
      .addParameter("relative_name", this.relative_name)
      .addParameter("img", this.img)
      .addParameter("relation", this.relation)
      .addParameter("user_id", this.user_id)
      .addParameter("relation_type_id", this.relation_type_id)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<Relative> all(){
    String sql = "SELECT * FROM relatives;";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Relative.class);
    }
  }

  @Override
  public boolean equals(Object otherRelative) {
    if (!(otherRelative instanceof Relative)) {
      return false;
    } else {
      Relative newRelative = (Relative) otherRelative;
      return this.getRelativeName().equals(newRelative.getRelativeName()) &&
             this.getUserId() == newRelative.getUserId() &&
             this.getRelation().equals(newRelative.getRelation()) &&
             this.getImg().equals(newRelative.getImg()) &&
             this.getRelationTypeId() == newRelative.getRelationTypeId();
    }
  }

  public static Relative find(int id){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM relatives WHERE id = :id";
      Relative newRelative = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Relative.class);
      return newRelative;
    }
  }

  public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE relatives SET relative_name = :name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM relatives WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }
}
