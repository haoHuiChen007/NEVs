package com.example.nevs.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import com.example.nevs.module.comment.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ITreeUtil {
    @Autowired
    private ICommentService commentService;
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

    public static void main(String[] args) {

    }
}
