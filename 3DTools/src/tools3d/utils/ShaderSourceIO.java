package tools3d.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.j3d.utils.shader.StringIO;

public class ShaderSourceIO
{
	public static boolean ES_SHADERS = false;

	public static String getTextFileAsString(String fileName)
	{
		InputStream is = null;
		try
		{
			String sourceCode;
			is = ShaderSourceIO.class.getResourceAsStream("/" + fileName);
			if (is != null)
			{
				sourceCode = StringIO.readFully(new BufferedReader(new InputStreamReader(is)));
			}
			else
			{
				sourceCode = StringIO.readFully(new File(fileName));
			}

			if (ES_SHADERS)
			{
				sourceCode = sourceCode.replace("#version 120", "#version 100");

				//TODO: also swap the normal swizzle!
				sourceCode = sourceCode.replace("vec4 normalMap = vec4( texture2D( NormalMap, offset ).ag * 2.0 - 1.0, 0.0, 0.0 );",
						"vec4 normal = texture2D( NormalMap, offset );");
				sourceCode = sourceCode.replace("normalMap.z = sqrt( 1.0 - dot( normalMap.xy,normalMap.xy ) );",
						"normal.rgb = normal.rgb * 2.0 - 1.0;");
			}

			return sourceCode;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (is != null)
					is.close();
			}
			catch (IOException e)
			{
			}
		}
		return null;
	}

}
