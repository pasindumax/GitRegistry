import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pasindu.gitregistry.data.model.Repo
import com.pasindu.gitregistry.ui.theme.Divider
import com.pasindu.gitregistry.ui.theme.HiColorText
import com.pasindu.gitregistry.ui.theme.MedColorText
import com.pasindu.gitregistry.ui.theme.Primary


@Composable
fun RepoListItem(
    repo: Repo,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = repo.owner.avatarUrl,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = repo.fullName,
                        style = MaterialTheme.typography.titleMedium,
                        color = HiColorText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "★",
                            color = Primary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = formatStarCount(repo.stargazersCount),
                            style = MaterialTheme.typography.labelMedium,
                            color = MedColorText
                        )
                    }
                }

                if (!repo.description.isNullOrBlank()) {
                    Text(
                        text = repo.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MedColorText,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        HorizontalDivider(
            color = Divider,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


private fun formatStarCount(count: Int): String {
    return when {
        count >= 1000 -> {
            val thousands = count / 1000.0
            if (thousands % 1.0 == 0.0) {
                "${thousands.toInt()}k"
            } else {
                String.format("%.1fk", thousands)
            }
        }
        else -> count.toString()
    }
}