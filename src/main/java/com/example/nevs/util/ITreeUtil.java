package com.example.nevs.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import java.lang.reflect.Field;
import java.util.List;

public abstract class ITreeUtil {
    public static <T> List<Tree<String>> getTree(List<T> list, Integer level, String rootId, List<String> ignoreFields) {
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("id").setDeep(level);
        return TreeUtil.build(list, rootId, config, (object, tree) -> {
            Field[] fields = ReflectUtil.getFieldsDirectly(object.getClass(), true);
            for (Field field : fields) {
                String fileName = field.getName();
                if (!ignoreFields.contains(fileName)) {
                    Object fieldValue = ReflectUtil.getFieldValue(object, field);
                    tree.putExtra(fileName, fieldValue);
                }
            }
        });
    }

    public static <T> List<Tree<String>> getTree(List<T> list, Integer level, String rootId) {
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("id").setDeep(level);
        return TreeUtil.build(list, rootId, config, (object, tree) -> {
            Field[] fields = ReflectUtil.getFieldsDirectly(object.getClass(), true);
            for (Field field : fields) {
                String fileName = field.getName();
                Object fieldValue = ReflectUtil.getFieldValue(object, field);
                tree.putExtra(fileName, fieldValue);
            }
        });
    }

//    public static void main(String[] args) {
//        List<Department> departments = CollUtil.newArrayList();
//        departments.add(new Department("1", "手術科", "0"));
//        departments.add(new Department("2", "肛肠科", "1"));
//        departments.add(new Department("3", "泌尿科", "2"));
//        departments.add(new Department("4", "急诊科", "3"));
//        departments.add(new Department("5", "精神科", "3"));
//        departments.add(new Department("6", "外科", "0"));
//        departments.add(new Department("7", "内科", "6"));
//        departments.add(new Department("8", "痔疮科", "7"));
//        List<Tree<String>> tree = getTree(departments, null, "0",CollUtil.newArrayList("serialVersionUID","num","level"));
//        System.out.println(JSONUtil.toJsonPrettyStr(tree));
//    }
}
