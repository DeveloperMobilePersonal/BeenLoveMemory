package com.teamdev.demngayyeu2020.views.wave;

import android.content.Context;

import com.github.siyamed.shapeimageview.path.parser.IoUtil;
import com.github.siyamed.shapeimageview.path.parser.PathInfo;
import com.github.siyamed.shapeimageview.path.parser.SvgToPath;
import com.teamdev.demngayyeu2020.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SvgLoader {
    private static final Map<String, PathInfo> PATH_MAP = new ConcurrentHashMap<String, PathInfo>();
    public static PathInfo readSvgFile(Context context, String pathFile, String id) {
        PathInfo pathInfo = PATH_MAP.get(id);
        if (pathInfo == null) {
            InputStream is = null;
            try {
                is = new FileInputStream(pathFile);
                pathInfo = SvgToPath.getSVGFromInputStream(is);
                PATH_MAP.put(id, pathInfo);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                is = context.getResources().openRawResource(R.raw.w_heart_1);
                pathInfo = SvgToPath.getSVGFromInputStream(is);
            } finally {
                IoUtil.closeQuitely(is);
            }
        }
        return pathInfo;
    }
}
