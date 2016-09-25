package com.app.hrms.ui.common;

import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Administrator on 2016/5/30.
 */
public class OrgUnitItem extends TreeNode {

    public String nodeId;
    public String nodeType;
    public String nodeName;
    public OrgUnitItem(String text, String nodeId, String nodeType) {
        super(text);
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.nodeName = text;
    }
}
