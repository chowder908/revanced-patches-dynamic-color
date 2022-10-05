package app.revanced.patches.youtube.layout.dynamiccolor.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.annotation.Version
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.*
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.layout.dynamiccolor.annotations.DynamicCompatibility
import app.revanced.patches.youtube.misc.manifest.patch.FixLocaleConfigErrorPatch
import org.w3c.dom.Element

@Patch
@DependsOn([FixLocaleConfigErrorPatch::class])
@Name("Dynamic-Color")
@Description("Applies a custom color using phone pallette.")
@DynamicCompatibility
@Version("0.0.1")
class ThemePatch : ResourcePatch {
    override fun execute(context: ResourceContext): PatchResult {
        context.xmlEditor["res/values/colors.xml"].use { editor ->
            val resourcesNode = editor.file.getElementsByTagName("resources").item(0) as Element

            for (i in 0 until resourcesNode.childNodes.length) {
                val node = resourcesNode.childNodes.item(i) as? Element ?: continue

                node.textContent = when (node.getAttribute("name")) {
                    "yt_youtube_red", "yt_brand_red", "video_progress_passed_indicator_color", "shorts_multi_segment_progress_bar_active_color", "text_color_blue", "yt_light_blue", "lc_button_style_primary_background", "yt_light_red" -> "@android:color/system_accent1_100"
                    "yt_medium_red" -> "@android:color/system_accent1_600"
                    "edit_location_suggestion_text_color_light" -> "@android:color/system_accent1_100"
                    "yt_selected_nav_label_light" -> "@android:color/system_accent1_100"
                    "active_timestamp_marker_color" -> "@android:color/system_accent1_100"
                    "crop_info_text" -> "@android:color/system_accent1_100"
                    "navigation_bar_background_light" -> "@android:color/system_accent1_100"
                    else -> continue
                }
            }
        }

        return PatchResultSuccess()
    }
}