package org.millenaire.common.annotedparameters;

import java.lang.reflect.Field;
import org.millenaire.common.culture.Culture;
import org.millenaire.common.item.InvItem;
import org.millenaire.common.utilities.MillLog;
import org.millenaire.common.utilities.Point;
import org.millenaire.common.village.Building;
import org.millenaire.common.village.buildingmanagers.ResManager;

public class AnnotedParameter {
  Field field;
  
  public final ParameterType type;
  
  public final String explanation;
  
  public final String explanationCategory;
  
  public final String configName;
  
  public final String defaultValueString;
  
  public static class BonusItem {
    public final InvItem item;
    
    public final int chance;
    
    public final String tag;
    
    public BonusItem(InvItem item, int chance) {
      this.item = item;
      this.chance = chance;
      this.tag = null;
    }
    
    public BonusItem(InvItem item, int chance, String tag) {
      this.item = item;
      this.chance = chance;
      this.tag = tag;
    }
  }
  
  public enum ParameterType {
    STRING((String)new ValueIO.StringIO()),
    STRINGDISPLAY((String)new ValueIO.StringDisplayIO()),
    STRING_LIST((String)new ValueIO.StringListIO()),
    STRING_ADD((String)new ValueIO.StringAddIO()),
    STRING_INVITEM_ADD((String)new ValueIO.StringInvItemAddIO()),
    STRING_CASE_SENSITIVE_ADD((String)new ValueIO.StringCaseSensitiveAddIO()),
    STRING_INTEGER_ADD((String)new ValueIO.StringNumberAddIO()),
    TRANSLATED_STRING_ADD((String)new ValueIO.TranslatedStringAddIO()),
    BOOLEAN((String)new ValueIO.BooleanIO()),
    INTEGER((String)new ValueIO.IntegerIO()),
    INTEGER_ARRAY((String)new ValueIO.IntegerArrayIO()),
    FLOAT((String)new ValueIO.FloatIO()),
    RESOURCE_LOCATION((String)new ValueIO.ResourceLocationIO()),
    MILLISECONDS((String)new ValueIO.MillisecondsIO()),
    INVITEM((String)new ValueIO.InvItemIO()),
    ITEMSTACK_ARRAY((String)new ValueIO.ItemStackArrayIO()),
    INVITEM_ADD((String)new ValueIO.InvItemAddIO()),
    INVITEM_PAIR((String)new ValueIO.InvItemPairIO()),
    INVITEM_NUMBER_ADD((String)new ValueIO.InvItemNumberAddIO()),
    INVITEM_PRICE_ADD((String)new ValueIO.InvItemPriceAddIO()),
    ENTITY_ID((String)new ValueIO.EntityIO()),
    BLOCK_ID((String)new ValueIO.BlockIdIO()),
    BLOCKSTATE((String)new ValueIO.BlockStateIO()),
    BLOCKSTATE_ADD((String)new ValueIO.BlockStateAddIO()),
    BONUS_ITEM_ADD((String)new ValueIO.BonusItemAddIO()),
    STARTING_ITEM_ADD((String)new ValueIO.StartingItemAddIO()),
    POS_TYPE((String)new ValueIO.PosTypeIO()),
    GOAL_ADD((String)new ValueIO.GoalAddIO()),
    TOOLCATEGORIES_ADD((String)new ValueIO.ToolCategoriesIO()),
    GENDER((String)new ValueIO.GenderIO()),
    DIRECTION((String)new ValueIO.DirectionIO()),
    CLOTHES((String)new ValueIO.ClothAddIO()),
    VILLAGERCONFIG((String)new ValueIO.VillagerConfigIO()),
    BUILDING((String)new CultureValueIO.BuildingSetIO()),
    BUILDING_ADD((String)new CultureValueIO.BuildingSetAddIO()),
    BUILDINGCUSTOM((String)new CultureValueIO.BuildingCustomIO()),
    BUILDINGCUSTOM_ADD((String)new CultureValueIO.BuildingCustomAddIO()),
    VILLAGER_ADD((String)new CultureValueIO.VillagerAddIO()),
    SHOP((String)new CultureValueIO.ShopIO()),
    RANDOM_BRICK_COLOUR_ADD((String)new ValueIO.RandomBrickColourAddIO()),
    BRICK_COLOUR_THEME_ADD((String)new ValueIO.BrickColourThemeAddIO()),
    WALL_TYPE((String)new CultureValueIO.WallIO());
    
    public ValueIO io;
    
    ParameterType(ValueIO parser) {
      this.io = parser;
    }
  }
  
  public enum PosType {
    CRAFTING("crafting"),
    DEFENDING("defending"),
    LEASURE("leasure"),
    SELLING("selling"),
    SHELTER("shelter"),
    SLEEPING("sleeping");
    
    String code;
    
    public static String getAllCodes() {
      String s = "";
      for (PosType posType : values())
        s = s + posType.code + " "; 
      return s;
    }
    
    public static PosType getByType(String code) {
      for (PosType posType : values()) {
        if (posType.code.equals(code.toLowerCase()))
          return posType; 
      } 
      return null;
    }
    
    PosType(String code) {
      this.code = code;
    }
    
    public Point getPosition(Building building) {
      ResManager resManager = building.getResManager();
      switch (this) {
        case CRAFTING:
          return resManager.getCraftingPos();
        case DEFENDING:
          return resManager.getDefendingPos();
        case LEASURE:
          return resManager.getLeasurePos();
        case SELLING:
          return resManager.getSellingPos();
        case SHELTER:
          return resManager.getShelterPos();
        case SLEEPING:
          return resManager.getSleepingPos();
      } 
      return null;
    }
  }
  
  public AnnotedParameter(Field field) {
    this.field = field;
    String configName = ((ConfigAnnotations.ConfigField)field.<ConfigAnnotations.ConfigField>getAnnotation(ConfigAnnotations.ConfigField.class)).paramName().toLowerCase();
    if (configName.length() == 0)
      configName = field.getName().toLowerCase(); 
    this.configName = configName;
    this.type = ((ConfigAnnotations.ConfigField)field.<ConfigAnnotations.ConfigField>getAnnotation(ConfigAnnotations.ConfigField.class)).type();
    String defaultValueString = ((ConfigAnnotations.ConfigField)field.<ConfigAnnotations.ConfigField>getAnnotation(ConfigAnnotations.ConfigField.class)).defaultValue();
    if (defaultValueString.length() > 0) {
      this.defaultValueString = defaultValueString;
    } else {
      this.defaultValueString = null;
    } 
    if (field.isAnnotationPresent((Class)ConfigAnnotations.FieldDocumentation.class)) {
      String explanation = ((ConfigAnnotations.FieldDocumentation)field.<ConfigAnnotations.FieldDocumentation>getAnnotation(ConfigAnnotations.FieldDocumentation.class)).explanation();
      if (explanation.length() > 0) {
        this.explanation = explanation;
      } else {
        this.explanation = null;
      } 
      String explanationCategory = ((ConfigAnnotations.FieldDocumentation)field.<ConfigAnnotations.FieldDocumentation>getAnnotation(ConfigAnnotations.FieldDocumentation.class)).explanationCategory();
      if (explanationCategory.length() == 0) {
        this.explanationCategory = explanationCategory;
      } else {
        this.explanationCategory = null;
      } 
    } else {
      this.explanation = null;
      this.explanationCategory = null;
    } 
  }
  
  public void parseValue(Culture culture, Object targetClass, String value) {
    value = value.trim();
    try {
      if (this.type.io.useCulture()) {
        this.type.io.readValueCulture(culture, targetClass, this.field, value);
      } else {
        this.type.io.readValue(targetClass, this.field, value);
      } 
    } catch (org.millenaire.common.utilities.MillLog.MillenaireException e) {
      MillLog.error(null, targetClass.toString() + ": Error when reading value '" + value + "' for parameter " + this.configName + ": " + e.getMessage());
    } catch (Exception e) {
      MillLog.printException(targetClass.toString() + ": Error when reading value '" + value + "' for parameter " + this.configName + ": ", e);
    } 
  }
}
